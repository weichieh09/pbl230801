export interface IVwEventResult {
  id?: number;
  eId?: number | null;
  pId?: number | null;
  mId?: number | null;
  winFg?: string | null;
  plyrNm?: string | null;
  plyrLvl?: string | null;
  mtchEndTime?: Date | null;
  totMatchs?: string | null;
  totWins?: string | null;
  acmlWins?: string | null;
  chkFg?: string | null;
}

export class VwEventResult implements IVwEventResult {
  constructor(
    public id?: number,
    public eId?: number | null,
    public pId?: number | null,
    public mId?: number | null,
    public winFg?: string | null,
    public plyrNm?: string | null,
    public plyrLvl?: string | null,
    public mtchEndTime?: Date | null,
    public totMatchs?: string | null,
    public totWins?: string | null,
    public acmlWins?: string | null,
    public chkFg?: string | null
  ) {}
}
