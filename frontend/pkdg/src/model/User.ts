export default interface User {
	username: string;
	email: string;
	token: string;
	role: UserRole;
}

export enum UserRole {
	ADMIN = "ADMIN",
	USER = "USER"
}
