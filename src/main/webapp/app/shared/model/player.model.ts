export interface IPlayer {
  id?: number;
  plyrNm?: string | null;
  plyrLvl?: string | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class Player implements IPlayer {
  constructor(
    public id?: number,
    public plyrNm?: string | null,
    public plyrLvl?: string | null,
    public lstMtnUsr?: string | null,
    public lstMtnDt?: Date | null
  ) {}
}
