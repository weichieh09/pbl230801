/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import VwWcc701ResultService from '@/entities/vw-wcc-701-result/vw-wcc-701-result.service';
import { VwWcc701Result } from '@/shared/model/vw-wcc-701-result.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('VwWcc701Result Service', () => {
    let service: VwWcc701ResultService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new VwWcc701ResultService();
      currentDate = new Date();
      elemDefault = new VwWcc701Result(
        123,
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        0,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            evntDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            mtchEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of VwWcc701Result', async () => {
        const returnedFromService = Object.assign(
          {
            eId: 1,
            evntNm: 'BBBBBB',
            evntDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            venue: 'BBBBBB',
            mId: 1,
            mtchEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            wPlyr1Id: 1,
            wPlyr1Lvl: 'BBBBBB',
            wPlyr1Nm: 'BBBBBB',
            wPlyr2Id: 1,
            wPlyr2Lvl: 'BBBBBB',
            wPlyr2Nm: 'BBBBBB',
            vs: 'BBBBBB',
            lPlyr1Id: 1,
            lPlyr1Lvl: 'BBBBBB',
            lPlyr1Nm: 'BBBBBB',
            lPlyr2Id: 1,
            lPlyr2Lvl: 'BBBBBB',
            lPlyr2Nm: 'BBBBBB',
            wScr: 'BBBBBB',
            lScr: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            evntDt: currentDate,
            mtchEndTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of VwWcc701Result', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
