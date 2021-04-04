import React from "react";

interface ErrorProps {
    message: string;
}

const ErrorPlaceholder: React.FC<ErrorProps> = ({message}) => {
    return (
        <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
            <h1>{message}</h1>
        </div>
    );
};

export default ErrorPlaceholder;