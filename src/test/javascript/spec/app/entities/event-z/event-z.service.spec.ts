/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import EventZService from '@/entities/event-z/event-z.service';
import { EventZ } from '@/shared/model/event-z.model';

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
  describe('EventZ Service', () => {
    let service: EventZService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new EventZService();
      currentDate = new Date();
      elemDefault = new EventZ(123, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            evntDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            eventBegTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            eventEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a EventZ', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            evntDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            eventBegTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            eventEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lstMtnDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            evntDt: currentDate,
            eventBegTime: currentDate,
            eventEndTime: currentDate,
            lstMtnDt: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a EventZ', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a EventZ', async () => {
        const returnedFromService = Object.assign(
          {
            evntNm: 'BBBBBB',
            evntDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            venue: 'BBBBBB',
            eventBegTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            eventEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lstMtnUsr: 'BBBBBB',
            lstMtnDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            evntDt: currentDate,
            eventBegTime: currentDate,
            eventEndTime: currentDate,
            lstMtnDt: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a EventZ', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a EventZ', async () => {
        const patchObject = Object.assign(
          {
            evntDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            eventBegTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            eventEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          new EventZ()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            evntDt: currentDate,
            eventBegTime: currentDate,
            eventEndTime: currentDate,
            lstMtnDt: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a EventZ', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of EventZ', async () => {
        const returnedFromService = Object.assign(
          {
            evntNm: 'BBBBBB',
            evntDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            venue: 'BBBBBB',
            eventBegTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            eventEndTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lstMtnUsr: 'BBBBBB',
            lstMtnDt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            evntDt: currentDate,
            eventBegTime: currentDate,
            eventEndTime: currentDate,
            lstMtnDt: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of EventZ', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a EventZ', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a EventZ', async () => {
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
