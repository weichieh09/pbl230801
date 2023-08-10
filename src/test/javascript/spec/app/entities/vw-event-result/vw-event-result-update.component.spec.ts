/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import VwEventResultUpdateComponent from '@/entities/vw-event-result/vw-event-result-update.vue';
import VwEventResultClass from '@/entities/vw-event-result/vw-event-result-update.component';
import VwEventResultService from '@/entities/vw-event-result/vw-event-result.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('VwEventResult Management Update Component', () => {
    let wrapper: Wrapper<VwEventResultClass>;
    let comp: VwEventResultClass;
    let vwEventResultServiceStub: SinonStubbedInstance<VwEventResultService>;

    beforeEach(() => {
      vwEventResultServiceStub = sinon.createStubInstance<VwEventResultService>(VwEventResultService);

      wrapper = shallowMount<VwEventResultClass>(VwEventResultUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          vwEventResultService: () => vwEventResultServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.vwEventResult = entity;
        vwEventResultServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vwEventResultServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.vwEventResult = entity;
        vwEventResultServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vwEventResultServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVwEventResult = { id: 123 };
        vwEventResultServiceStub.find.resolves(foundVwEventResult);
        vwEventResultServiceStub.retrieve.resolves([foundVwEventResult]);

        // WHEN
        comp.beforeRouteEnter({ params: { vwEventResultId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.vwEventResult).toBe(foundVwEventResult);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});