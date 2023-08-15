export interface IVwWcc701Result {
  id?: number;
  eId?: number | null;
  evntNm?: string | null;
  evntDt?: Date | null;
  venue?: string | null;
  mId?: number | null;
  mtchEndTime?: Date | null;
  wPlyr1Id?: number | null;
  wPlyr1Lvl?: string | null;
  wPlyr1Nm?: string | null;
  wPlyr2Id?: number | null;
  wPlyr2Lvl?: string | null;
  wPlyr2Nm?: string | null;
  vs?: string | null;
  lPlyr1Id?: number | null;
  lPlyr1Lvl?: string | null;
  lPlyr1Nm?: string | null;
  lPlyr2Id?: number | null;
  lPlyr2Lvl?: string | null;
  lPlyr2Nm?: string | null;
  wScr?: string | null;
  lScr?: string | null;
}

export class VwWcc701Result implements IVwWcc701Result {
  constructor(
    public id?: number,
    public eId?: number | null,
    public evntNm?: string | null,
    public evntDt?: Date | null,
    public venue?: string | null,
    public mId?: number | null,
    public mtchEndTime?: Date | null,
    public wPlyr1Id?: number | null,
    public wPlyr1Lvl?: string | null,
    public wPlyr1Nm?: string | null,
    public wPlyr2Id?: number | null,
    public wPlyr2Lvl?: string | null,
    public wPlyr2Nm?: string | null,
    public vs?: string | null,
    public lPlyr1Id?: number | null,
    public lPlyr1Lvl?: string | null,
    public lPlyr1Nm?: string | null,
    public lPlyr2Id?: number | null,
    public lPlyr2Lvl?: string | null,
    public lPlyr2Nm?: string | null,
    public wScr?: string | null,
    public lScr?: string | null
  ) {}
}
