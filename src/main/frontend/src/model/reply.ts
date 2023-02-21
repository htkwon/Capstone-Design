import { Posting } from "./posting";
import { User } from "./user";

export interface Reply {
  uuid: String;
  writer: User;
  reactedPosting: Posting;
  article: String;
  createdDate: String;
  modifiedDate?: String;
  reports: number;
  isChosen?: boolean;
  isParent?: boolean;
}
