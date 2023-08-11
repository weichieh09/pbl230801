/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PlayerDetailComponent from '@/entities/player/player-details.vue';
import PlayerClass from '@/entities/player/player-details.component';
import PlayerService from '@/entities/player/player.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Player Management Detail Component', () => {
    let wrapper: Wrapper<PlayerClass>;
    let comp: PlayerClass;
    let playerServiceStub: SinonStubbedInstance<PlayerService>;

    beforeEach(() => {
      playerServiceStub = sinon.createStubInstance<PlayerService>(PlayerService);

      wrapper = shallowMount<PlayerClass>(PlayerDetailComponent, {
        store,
        localVue,
        router,
        provide: { playerService: () => playerServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPlayer = { id: 123 };
        playerServiceStub.find.resolves(foundPlayer);

        // WHEN
        comp.retrievePlayer(123);
        await comp.$nextTick();

        // THEN
        expect(comp.player).toBe(foundPlayer);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPlayer = { id: 123 };
        playerServiceStub.find.resolves(foundPlayer);

        // WHEN
        comp.beforeRouteEnter({ params: { playerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.player).toBe(foundPlayer);
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
