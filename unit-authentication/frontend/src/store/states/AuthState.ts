import User from "../../domain/User";
import OperationState from "../../components/common/OperationState";
import AuthStatus from "./AuthStatus";

export interface AuthState {
    user?: User;
    status: AuthStatus,
    loginOperationStatus: OperationState;
    logoutOperationStatus: OperationState;
    error?: string;
}