import {Dispatch} from "redux";
import LoginDto from "../../dtos/LoginDto";
import {AuthAction, AuthActionTypes} from "../actions/authAction";
import AuthService from "../../services/AuthService";
import ErrorHandler from "../../services/ErrorHandler";

export const login = (dto: LoginDto) => {
    return async (dispatch: Dispatch<AuthAction>) => {
        try {
            dispatch({type: AuthActionTypes.LOGIN_REQUESTED});
            const user = await AuthService.login(dto);
            setTimeout(() => {
                dispatch({type: AuthActionTypes.LOGIN_COMPLETED, payload: user});
            }, 500);
        } catch (e) {
            console.log(e);
            const message = ErrorHandler.isApiError(e) ? e.response.data.message : 'Error occurred while trying to login.';
            dispatch({
                type: AuthActionTypes.LOGIN_FAILED,
                payload: message,
            });
        }
    };
};

export const autologin = () => {
    return async (dispatch: Dispatch<AuthAction>) => {
        try {
            dispatch({type: AuthActionTypes.LOGIN_REQUESTED});
            const user = await AuthService.getProfile();
            setTimeout(() => {
                dispatch({type: AuthActionTypes.LOGIN_COMPLETED, payload: user});
            }, 500);
        } catch (e) {
            dispatch({
                type: AuthActionTypes.LOGIN_FAILED,
                payload: undefined,
            });
        }
    };
};

export const autologout = () => {
    return async (dispatch: Dispatch<AuthAction>) => {
        dispatch({type: AuthActionTypes.LOGIN_EXPIRED});
    };
};

export const logout = () => {
    return async (dispatch: Dispatch<AuthAction>) => {
        try {
            dispatch({type: AuthActionTypes.LOGOUT_REQUESTED});
            await AuthService.logout();
            dispatch({type: AuthActionTypes.LOGOUT_COMPLETED});
        } catch (e) {
            dispatch({
                type: AuthActionTypes.LOGIN_FAILED,
            });
        }
    };
};