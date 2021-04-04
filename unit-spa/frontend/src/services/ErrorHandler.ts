export default class ErrorHandler {

    static getMessage(e: any, fallbackMessage: string) {
        if (ErrorHandler.isApiError(e)) {
            const message = e.response.data.message;
            return message ? message : "Connection error. Please try again later."
        } else {
            return fallbackMessage;
        }
    }

    private static isApiError(e: any) {
        return e.response !== undefined;
    }

}

