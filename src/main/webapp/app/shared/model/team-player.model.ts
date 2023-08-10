export interface ITeamPlayer {
  id?: number;
  tId?: number | null;
  pId?: number | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class TeamPlayer implements ITeamPlayer {
  constructor(
    public id?: number,
    public tId?: number | null,
    public pId?: number | null,
    public lstMtnUsr?: string | null,
    public lstMtnDt?: Date | null
  ) {}
}
