import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IVwEventResult } from '@/shared/model/vw-event-result.model';

const baseApiUrl = 'api/vw-event-results';

export default class VwEventResultService {
  public find(id: number): Promise<IVwEventResult> {
    return new Promise<IVwEventResult>((resolve, reject) => {
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
