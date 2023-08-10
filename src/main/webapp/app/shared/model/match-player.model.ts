export interface IMatchPlayer {
  id?: number;
  mId?: number | null;
  pId?: number | null;
  eId?: number | null;
  mtchEndTime?: Date | null;
  score?: string | null;
  winFg?: string | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class MatchPlayer implements IMatchPlayer {
  constructor(
    public id?: number,
    public mId?: number | null,
    public pId?: number | null,
    public eId?: number | null,
    public mtchEndTime?: Date | null,
    public score?: string | null,
    public winFg?: string | null,
    public lstMtnUsr?: string | null,
    public lstMtnDt?: Date | null
  ) {}
}
