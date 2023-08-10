export interface IMatchZ {
  id?: number;
  eId?: number | null;
  mtchEndTime?: Date | null;
  wPlyr1?: string | null;
  wPlyr2?: string | null;
  wScr?: string | null;
  lPlyr1?: string | null;
  lPlyr2?: string | null;
  lScr?: string | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class MatchZ implements IMatchZ {
  constructor(
    public id?: number,
    public eId?: number | null,
    public mtchEndTime?: Date | null,
    public wPlyr1?: string | null,
    public wPlyr2?: string | null,
    public wScr?: string | null,
    public lPlyr1?: string | null,
    public lPlyr2?: string | null,
    public lScr?: string | null,
    public lstMtnUsr?: string | null,
    public lstMtnDt?: Date | null
  ) {}
}
