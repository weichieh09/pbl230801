import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IVwWcc701Result } from '@/shared/model/vw-wcc-701-result.model';

const baseApiUrl = 'api/vw-wcc-701-results';

export default class VwWcc701ResultService {
  public find(id: number): Promise<IVwWcc701Result> {
    return new Promise<IVwWcc701Result>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
