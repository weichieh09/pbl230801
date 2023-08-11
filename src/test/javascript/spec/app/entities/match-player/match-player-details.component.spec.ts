/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MatchPlayerDetailComponent from '@/entities/match-player/match-player-details.vue';
import MatchPlayerClass from '@/entities/match-player/match-player-details.component';
import MatchPlayerService from '@/entities/match-player/match-player.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MatchPlayer Management Detail Component', () => {
    let wrapper: Wrapper<MatchPlayerClass>;
    let comp: MatchPlayerClass;
    let matchPlayerServiceStub: SinonStubbedInstance<MatchPlayerService>;

    beforeEach(() => {
      matchPlayerServiceStub = sinon.createStubInstance<MatchPlayerService>(MatchPlayerService);

      wrapper = shallowMount<MatchPlayerClass>(MatchPlayerDetailComponent, {
        store,
        localVue,
        router,
        provide: { matchPlayerService: () => matchPlayerServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatchPlayer = { id: 123 };
        matchPlayerServiceStub.find.resolves(foundMatchPlayer);

        // WHEN
        comp.retrieveMatchPlayer(123);
        await comp.$nextTick();

        // THEN
        expect(comp.matchPlayer).toBe(foundMatchPlayer);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMatchPlayer = { id: 123 };
        matchPlayerServiceStub.find.resolves(foundMatchPlayer);

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
