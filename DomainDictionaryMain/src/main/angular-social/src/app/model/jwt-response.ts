import {User} from "./user";

export class JwtResponse {
  token ?: string;
  user ?: User;

  constructor(token: string, user:User) {
    this.token = token;
    this.user = user;
  }
}
