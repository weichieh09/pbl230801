export interface IUserTeam {
  id?: number;
  uId?: number | null;
  tId?: number | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class UserTeam implements IUserTeam {
  constructor(
    public id?: number,
    public uId?: number | null,
    public tId?: number | null,
    public lstMtnUsr?: string | null,
    public lstMtnDt?: Date | null
  ) {}
}
