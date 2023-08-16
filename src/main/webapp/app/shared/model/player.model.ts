export interface IPlayer {
  id?: number;
  plyrNm?: string | null;
  plyrLvl?: number | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class Player implements IPlayer {
  constructor(
    public id?: number,
    public plyrNm?: string | null,
    public plyrLvl?: number | null,
    public lstMtnUsr?: string | null,
    public lstMtnDt?: Date | null
  ) {}
}
