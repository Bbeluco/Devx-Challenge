import { ResponseChallengesInterface } from "./responseChallengesInterface";

export interface FormEmailInterface {
    handlerResponse: (r: ResponseChallengesInterface) => void
    handlerEmail: (email: string) => void;
}