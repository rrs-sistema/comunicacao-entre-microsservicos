class UserException extends Error {
    constructor(status, message) {
        super(message);
        this.status = status;
        this.message = message;
        this.name = this.costructior.name;
        Error.captureStackTrace(this.costructior);
    }
}

export default UserException;