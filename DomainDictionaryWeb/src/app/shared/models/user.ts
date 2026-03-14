export interface User {
  id: number;
  name: string;
  email: string;
  role: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  name: string;
  email: string;
  password: string;
}

export interface JwtResponse {
  token: string;
  id: number;
  name: string;
  email: string;
  role: string;
}
