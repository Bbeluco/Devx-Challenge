import { ResponseChallengesInterface } from "./responseChallengesInterface";

export interface MfaInterface {
    email: string;
    response: ResponseChallengesInterface;
    handlerResponse: (r: ResponseChallengesInterface) => void
}