//-------------INTERFACE-------------

/*
* 게시판 유형 (추후 더 추가될 수 있음)
*/
export enum BoardType {
    free,
    question,
    recruit,
    notice,
    summary
}

export interface Board {
   type: BoardType;
   id: string;
   title: String;
   context: String;
   nickname: String;
   profileImg?: String;
   studentId: String;
   time: String;
   bookmark: number;
   comment: Array<Reply>
   report: number;
   //todo(은서): 게시판 성격따라서 다른 옵션 추가해야할듯합니다.
}

export interface Reply {
    id: String;
    nickname: String;
    context: String;
    time: String;
    report: number;
    isChosen?: boolean;
    isParent?: boolean;
}