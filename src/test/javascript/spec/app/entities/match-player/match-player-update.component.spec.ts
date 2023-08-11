/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import MatchPlayerUpdateComponent from '@/entities/match-player/match-player-update.vue';
import MatchPlayerClass from '@/entities/match-player/match-player-update.component';
import MatchPlayerService from '@/entities/match-player/match-player.service';

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
  describe('MatchPlayer Management Update Component', () => {
    let wrapper: Wrapper<MatchPlayerClass>;
    let comp: MatchPlayerClass;
    let matchPlayerServiceStub: SinonStubbedInstance<MatchPlayerService>;

    beforeEach(() => {
      matchPlayerServiceStub = sinon.createStubInstance<MatchPlayerService>(MatchPlayerService);

      wrapper = shallowMount<MatchPlayerClass>(MatchPlayerUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          matchPlayerService: () => matchPlayerServiceStub,
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
        comp.matchPlayer = entity;
        matchPlayerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchPlayerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.matchPlayer = entity;
        matchPlayerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchPlayerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMatchPlayer = { id: 123 };
        matchPlayerServiceStub.find.resolves(foundMatchPlayer);
        matchPlayerServiceStub.retrieve.resolves([foundMatchPlayer]);

        // WHEN
        comp.beforeRouteEnter({ params: { matchPlayerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.matchPlayer).toBe(foundMatchPlayer);
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
