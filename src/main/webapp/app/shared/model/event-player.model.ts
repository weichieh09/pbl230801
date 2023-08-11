export interface IEventPlayer {
  id?: number;
  eId?: number | null;
  pId?: number | null;
  chkFg?: string | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class EventPlayer implements IEventPlayer {
  constructor(
    public id?: number,
    public eId?: number | null,
    public pId?: number | null,
    public chkFg?: string | null,
    public lstMtnUsr?: string | null,
    public lstMtnDt?: Date | null
  ) {}
}
