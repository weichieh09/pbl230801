/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import MatchZService from '@/entities/match-z/match-z.service';
import { MatchZ } from '@/shared/model/match-z.model';

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
  describe('MatchZ Service', () => {
    let service: MatchZService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new MatchZService();
      currentDate = new Date();
      elemDefault = new MatchZ(
        123,
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            mtchEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lstMtnDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a MatchZ', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            mtchEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lstMtnDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            mtchEndTime: currentDate,
            lstMtnDt: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a MatchZ', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a MatchZ', async () => {
        const returnedFromService = Object.assign(
          {
            eId: 1,
            mtchEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            wPlyr1: 'BBBBBB',
            wPlyr2: 'BBBBBB',
            wScr: 'BBBBBB',
            lPlyr1: 'BBBBBB',
            lPlyr2: 'BBBBBB',
            lScr: 'BBBBBB',
            lstMtnUsr: 'BBBBBB',
            lstMtnDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            mtchEndTime: currentDate,
            lstMtnDt: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a MatchZ', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a MatchZ', async () => {
        const patchObject = Object.assign(
          {
            mtchEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lPlyr1: 'BBBBBB',
            lScr: 'BBBBBB',
            lstMtnUsr: 'BBBBBB',
            lstMtnDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          new MatchZ()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            mtchEndTime: currentDate,
            lstMtnDt: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a MatchZ', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of MatchZ', async () => {
        const returnedFromService = Object.assign(
          {
            eId: 1,
            mtchEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            wPlyr1: 'BBBBBB',
            wPlyr2: 'BBBBBB',
            wScr: 'BBBBBB',
            lPlyr1: 'BBBBBB',
            lPlyr2: 'BBBBBB',
            lScr: 'BBBBBB',
            lstMtnUsr: 'BBBBBB',
            lstMtnDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            mtchEndTime: currentDate,
            lstMtnDt: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of MatchZ', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a MatchZ', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a MatchZ', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
