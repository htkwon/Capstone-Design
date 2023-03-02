import { User } from "./user";

export interface Reply {
  uuid: String;
  writer: User;
  article: String;
  createdDate: String;
  modifiedDate?: String;
  reports: number;
  isChosen?: boolean;
  isParent?: boolean;
}
