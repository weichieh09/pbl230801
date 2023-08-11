/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EventPlayerDetailComponent from '@/entities/event-player/event-player-details.vue';
import EventPlayerClass from '@/entities/event-player/event-player-details.component';
import EventPlayerService from '@/entities/event-player/event-player.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EventPlayer Management Detail Component', () => {
    let wrapper: Wrapper<EventPlayerClass>;
    let comp: EventPlayerClass;
    let eventPlayerServiceStub: SinonStubbedInstance<EventPlayerService>;

    beforeEach(() => {
      eventPlayerServiceStub = sinon.createStubInstance<EventPlayerService>(EventPlayerService);

      wrapper = shallowMount<EventPlayerClass>(EventPlayerDetailComponent, {
        store,
        localVue,
        router,
        provide: { eventPlayerService: () => eventPlayerServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEventPlayer = { id: 123 };
        eventPlayerServiceStub.find.resolves(foundEventPlayer);

        // WHEN
        comp.retrieveEventPlayer(123);
        await comp.$nextTick();

        // THEN
        expect(comp.eventPlayer).toBe(foundEventPlayer);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEventPlayer = { id: 123 };
        eventPlayerServiceStub.find.resolves(foundEventPlayer);

        // WHEN
        comp.beforeRouteEnter({ params: { eventPlayerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.eventPlayer).toBe(foundEventPlayer);
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
