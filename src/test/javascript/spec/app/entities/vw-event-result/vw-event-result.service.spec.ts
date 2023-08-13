/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import VwEventResultService from '@/entities/vw-event-result/vw-event-result.service';
import { VwEventResult } from '@/shared/model/vw-event-result.model';

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
  describe('VwEventResult Service', () => {
    let service: VwEventResultService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new VwEventResultService();
      currentDate = new Date();
      elemDefault = new VwEventResult(
        123,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
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

      it('should return a list of VwEventResult', async () => {
        const returnedFromService = Object.assign(
          {
            eId: 1,
            tId: 1,
            pId: 1,
            mId: 1,
            winFg: 'BBBBBB',
            plyrNm: 'BBBBBB',
            plyrLvl: 'BBBBBB',
            mtchEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            totMatchs: 'BBBBBB',
            totWins: 'BBBBBB',
            acmlWins: 'BBBBBB',
            chkFg: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            mtchEndTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of VwEventResult', async () => {
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
