/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.henry.windowManagerTest.FPS;

import android.content.Context;


import java.util.Map;
import java.util.TreeMap;

/**
 * Each time a frame is drawn, records whether it should have expected any more callbacks since
 * the last time a frame was drawn (i.e. was a frame skipped?). Uses this plus total elapsed time
 * to determine FPS. Can also record total and expected frame counts, though NB, since the expected
 * frame rate is estimated, the expected frame count will lose accuracy over time.
 *
 * Also records the JS FPS, i.e. the frames per second with which either JS updated the UI or was
 * idle and not trying to update the UI. This is different from the FPS above since JS rendering is
 * async.
 */
public class FpsDebugFrameCallback extends ChoreographerCompat.FrameCallback {

  public static class FpsInfo {

    public final int totalFrames;
    public final int totalJsFrames;
    public final int totalExpectedFrames;
    public final int total4PlusFrameStutters;
    public final double fps;
    public final double jsFps;
    public final int totalTimeMs;

    public FpsInfo(
        int totalFrames,
        int totalJsFrames,
        int totalExpectedFrames,
        int total4PlusFrameStutters,
        double fps,
        double jsFps,
        int totalTimeMs) {
      this.totalFrames = totalFrames;
      this.totalJsFrames = totalJsFrames;
      this.totalExpectedFrames = totalExpectedFrames;
      this.total4PlusFrameStutters = total4PlusFrameStutters;
      this.fps = fps;
      this.jsFps = jsFps;
      this.totalTimeMs = totalTimeMs;
    }
  }

  private static final double EXPECTED_FRAME_TIME = 16.9;

  private final ChoreographerCompat mChoreographer;
  private Context mReactContext;
  private boolean mShouldStop = false;
  private long mFirstFrameTime = -1;
  private long mLastFrameTime = -1;
  private int mNumFrameCallbacks = 0;
  private int mExpectedNumFramesPrev = 0;
  private int m4PlusFrameStutters = 0;
  private int mNumFrameCallbacksWithBatchDispatches = 0;
  private boolean mIsRecordingFpsInfoAtEachFrame = false;
  private  TreeMap<Long, FpsInfo> mTimeToFps;

  public FpsDebugFrameCallback(ChoreographerCompat choreographer, Context reactContext) {
    mChoreographer = choreographer;
    mReactContext = reactContext;
  }

  @Override
  public void doFrame(long l) {
    if (mShouldStop) {
      return;
    }

    if (mFirstFrameTime == -1) {
      mFirstFrameTime = l;
    }

    long lastFrameStartTime = mLastFrameTime;
    mLastFrameTime = l;

    // TODO: 2017/8/24 这里不知道什么含义
    if (true) {
      mNumFrameCallbacksWithBatchDispatches++;
    }

    mNumFrameCallbacks++;
    int expectedNumFrames = getExpectedNumFrames();
    int framesDropped = expectedNumFrames - mExpectedNumFramesPrev - 1;
    if (framesDropped >= 4) {
      m4PlusFrameStutters++;
    }

    if (mIsRecordingFpsInfoAtEachFrame) {
      Assertions.assertNotNull(mTimeToFps);
      FpsInfo info = new FpsInfo(
          getNumFrames(),
          getNumJSFrames(),
          expectedNumFrames,
          m4PlusFrameStutters,
          getFPS(),
          getJSFPS(),
          getTotalTimeMS());
      mTimeToFps.put(System.currentTimeMillis(), info);
    }
    mExpectedNumFramesPrev = expectedNumFrames;

    mChoreographer.postFrameCallback(this);
  }

  public void start() {
    mShouldStop = false;
    //mReactContext.getCatalystInstance().addBridgeIdleDebugListener(
    //    mDidJSUpdateUiDuringFrameDetector);
    //mUIManagerModule.setViewHierarchyUpdateDebugListener(mDidJSUpdateUiDuringFrameDetector);
    mChoreographer.postFrameCallback(this);
  }

  public void startAndRecordFpsAtEachFrame() {
    mTimeToFps = new TreeMap<Long, FpsInfo>();
    mIsRecordingFpsInfoAtEachFrame = true;
    start();
  }

  public void stop() {
    mShouldStop = true;
    //mReactContext.getCatalystInstance().removeBridgeIdleDebugListener(
    //    mDidJSUpdateUiDuringFrameDetector);
    //mUIManagerModule.setViewHierarchyUpdateDebugListener(null);
  }

  public double getFPS() {
    if (mLastFrameTime == mFirstFrameTime) {
      return 0;
    }
    return ((double) (getNumFrames()) * 1e9) / (mLastFrameTime - mFirstFrameTime);
  }

  public double getJSFPS() {
    if (mLastFrameTime == mFirstFrameTime) {
      return 0;
    }
    return ((double) (getNumJSFrames()) * 1e9) / (mLastFrameTime - mFirstFrameTime);
  }

  public int getNumFrames() {
    return mNumFrameCallbacks - 1;
  }

  public int getNumJSFrames() {
    return mNumFrameCallbacksWithBatchDispatches - 1;
  }

  public int getExpectedNumFrames() {
    double totalTimeMS = getTotalTimeMS();
    int expectedFrames = (int) (totalTimeMS / EXPECTED_FRAME_TIME + 1);
    return expectedFrames;
  }

  public int get4PlusFrameStutters() {
    return m4PlusFrameStutters;
  }

  public int getTotalTimeMS() {
    return (int) ((double) mLastFrameTime - mFirstFrameTime) / 1000000;
  }

  /**
   * Returns the FpsInfo as if stop had been called at the given upToTimeMs. Only valid if
   * monitoring was started with {@link #startAndRecordFpsAtEachFrame()}.
   */
  public  FpsInfo getFpsInfo(long upToTimeMs) {
    Assertions.assertNotNull(mTimeToFps, "FPS was not recorded at each frame!");
    Map.Entry<Long, FpsInfo> bestEntry = mTimeToFps.floorEntry(upToTimeMs);
    if (bestEntry == null) {
      return null;
    }
    return bestEntry.getValue();
  }

  public void reset() {
    mFirstFrameTime = -1;
    mLastFrameTime = -1;
    mNumFrameCallbacks = 0;
    m4PlusFrameStutters = 0;
    mNumFrameCallbacksWithBatchDispatches = 0;
    mIsRecordingFpsInfoAtEachFrame = false;
    mTimeToFps = null;
  }
}
