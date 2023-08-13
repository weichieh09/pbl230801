export interface ITeamEvent {
  id?: number;
  tId?: number | null;
  eId?: number | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class TeamEvent implements ITeamEvent {
  constructor(
    public id?: number,
    public tId?: number | null,
    public eId?: number | null,
    public lstMtnUsr?: string | null,
    public lstMtnDt?: Date | null
  ) {}
}
