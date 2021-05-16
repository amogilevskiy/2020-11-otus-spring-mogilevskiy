import User from "../../domain/User";

export enum AuthActionTypes {
    LOGIN_REQUESTED = 'LOGIN_REQUESTED',
    LOGIN_COMPLETED = 'LOGIN_COMPLETED',
    LOGIN_FAILED = 'LOGIN_FAILED',
    LOGIN_EXPIRED = 'LOGIN_EXPIRED',
    LOGOUT_REQUESTED = 'LOGOUT_REQUESTED',
    LOGOUT_COMPLETED = 'LOGOUT_COMPLETED',
    LOGOUT_FAILED = 'LOGOUT_FAILED',
}


interface LoginRequestedAction {
    type: AuthActionTypes.LOGIN_REQUESTED;
}

interface LoginCompletedAction {
    type: AuthActionTypes.LOGIN_COMPLETED;
    payload: User;
}

interface LoginFailedAction {
    type: AuthActionTypes.LOGIN_FAILED;
    payload?: string;
}

interface LoginExpiredAction {
    type: AuthActionTypes.LOGIN_EXPIRED;
}

interface LogoutRequestedAction {
    type: AuthActionTypes.LOGOUT_REQUESTED;
}

interface LogoutCompletedAction {
    type: AuthActionTypes.LOGOUT_COMPLETED;
}

interface LogoutFailedAction {
    type: AuthActionTypes.LOGOUT_FAILED;
    payload?: string;
}


export type AuthAction = LoginRequestedAction | LoginCompletedAction | LoginFailedAction | LoginExpiredAction |
    LogoutRequestedAction | LogoutCompletedAction | LogoutFailedAction;