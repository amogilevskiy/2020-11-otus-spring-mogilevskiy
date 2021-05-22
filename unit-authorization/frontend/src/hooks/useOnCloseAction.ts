import {useEffect, useRef} from "react";

export const useOnCloseAction = (action: Function, visible: boolean) => {
    const prevVisibleRef = useRef<boolean>();

    useEffect(() => {
        prevVisibleRef.current = visible;
    }, [visible]);

    const prevVisible = prevVisibleRef.current;

    useEffect(() => {
        if (!visible && prevVisible) {
            if (action) {
                action();
            }
        }
    }, [visible]);

};