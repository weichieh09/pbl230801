export interface IProd {
  id?: number;
  prodNo?: string | null;
  chName?: string | null;
  issuDt?: Date | null;
  expDt?: Date | null;
}

export class Prod implements IProd {
  constructor(
    public id?: number,
    public prodNo?: string | null,
    public chName?: string | null,
    public issuDt?: Date | null,
    public expDt?: Date | null
  ) {}
}
