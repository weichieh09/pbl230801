export interface IEventZ {
  id?: number;
  evntNm?: string | null;
  evntDt?: Date | null;
  venue?: string | null;
  eventBegTime?: Date | null;
  eventEndTime?: Date | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class EventZ implements IEventZ {
  constructor(
    public id?: number,
    public evntNm?: string | null,
    public evntDt?: Date | null,
    public venue?: string | null,
    public eventBegTime?: Date | null,
    public eventEndTime?: Date | null,
    public lstMtnUsr?: string | null,
    public lstMtnDt?: Date | null
  ) {}
}
