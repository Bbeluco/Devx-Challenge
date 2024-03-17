import { ResponseChallengesInterface } from "./responseChallengesInterface";

export interface PasswordInterface {
    email: string,
    response: ResponseChallengesInterface;
    handlerResponse: (r: ResponseChallengesInterface) => void
    handlerJwttoken: (token: string) => void
}