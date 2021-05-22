import {AuthState} from "../states/AuthState";
import {AuthAction, AuthActionTypes} from "../actions/authAction";
import OperationState from "../../components/common/OperationState";
import AuthStatus from "../states/AuthStatus";

const initialState: AuthState = {
    user: undefined,
    status: AuthStatus.UNKNOWN,
    loginOperationStatus: OperationState.IDLE,
    logoutOperationStatus: OperationState.IDLE,
    error: undefined,
};

export const authReducer = (state = initialState, action: AuthAction): AuthState => {
    switch (action.type) {
        case AuthActionTypes.LOGIN_REQUESTED:
            return {
                loginOperationStatus: OperationState.PROGRESS,
                logoutOperationStatus: OperationState.IDLE,
                error: undefined,
                user: undefined,
                status: initialState.status,
            };

        case AuthActionTypes.LOGIN_COMPLETED:
            return {
                loginOperationStatus: OperationState.COMPLETED,
                logoutOperationStatus: OperationState.IDLE,
                error: undefined,
                user: action.payload,
                status: AuthStatus.AUTHORIZED,
            };

        case AuthActionTypes.LOGIN_FAILED:
            return {
                loginOperationStatus: OperationState.FAILED,
                logoutOperationStatus: OperationState.IDLE,
                error: action.payload,
                user: undefined,
                status: AuthStatus.UNAUTHORIZED,
            };

        case AuthActionTypes.LOGIN_EXPIRED:
            return {
                loginOperationStatus: OperationState.IDLE,
                logoutOperationStatus: OperationState.IDLE,
                error: undefined,
                user: undefined,
                status: AuthStatus.EXPIRED,
            };

        case AuthActionTypes.LOGOUT_REQUESTED:
            return {
                loginOperationStatus: OperationState.IDLE,
                logoutOperationStatus: OperationState.PROGRESS,
                error: initialState.error,
                user: initialState.user,
                status: initialState.status,
            };

        case AuthActionTypes.LOGOUT_COMPLETED:
            return {
                loginOperationStatus: OperationState.IDLE,
                logoutOperationStatus: OperationState.COMPLETED,
                error: undefined,
                user: undefined,
                status: AuthStatus.UNAUTHORIZED,
            };

        case AuthActionTypes.LOGOUT_FAILED:
            return {
                loginOperationStatus: OperationState.IDLE,
                logoutOperationStatus: OperationState.FAILED,
                error: undefined,
                user: initialState.user,
                status: initialState.status,
            };

        default:
            return state;
    }
};