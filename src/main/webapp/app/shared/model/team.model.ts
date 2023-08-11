export interface ITeam {
  id?: number;
  teamNm?: string | null;
  lstMtnUsr?: string | null;
  lstMtnDt?: Date | null;
}

export class Team implements ITeam {
  constructor(public id?: number, public teamNm?: string | null, public lstMtnUsr?: string | null, public lstMtnDt?: Date | null) {}
}
