import {useEffect, useState} from "react";
import AuthService from "../services/AuthService";
import User from "../domain/User";

export default function useCurrentUser() {
    const [user, setUser] = useState<User>();
    const [isLoading, setLoading] = useState(true);
    useEffect(() => {
        AuthService.getProfile()
            .then(user => {
                setUser(user);
                setLoading(false);
            }).catch(e => {
            setLoading(false);
        });
    }, []);
    return {
        user,
        isLoading,
    };
}