package com.henry.projectPractice.bean;


/**
 * LoginBean
 */

public class ResponseBean {
    private int code;
    private String message;
    private Result result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public static class Result {
        private String account;
        private String createdAt;
        private String deletedAt;
        private int id;
        private int level;
        private String token;
        private String updatedAt;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "account='" + account + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", deletedAt='" + deletedAt + '\'' +
                    ", id=" + id +
                    ", level=" + level +
                    ", token='" + token + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    '}';
        }
    }
}
