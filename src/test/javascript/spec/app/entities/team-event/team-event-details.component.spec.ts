/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TeamEventDetailComponent from '@/entities/team-event/team-event-details.vue';
import TeamEventClass from '@/entities/team-event/team-event-details.component';
import TeamEventService from '@/entities/team-event/team-event.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TeamEvent Management Detail Component', () => {
    let wrapper: Wrapper<TeamEventClass>;
    let comp: TeamEventClass;
    let teamEventServiceStub: SinonStubbedInstance<TeamEventService>;

    beforeEach(() => {
      teamEventServiceStub = sinon.createStubInstance<TeamEventService>(TeamEventService);

      wrapper = shallowMount<TeamEventClass>(TeamEventDetailComponent, {
        store,
        localVue,
        router,
        provide: { teamEventService: () => teamEventServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTeamEvent = { id: 123 };
        teamEventServiceStub.find.resolves(foundTeamEvent);

        // WHEN
        comp.retrieveTeamEvent(123);
        await comp.$nextTick();

        // THEN
        expect(comp.teamEvent).toBe(foundTeamEvent);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeamEvent = { id: 123 };
        teamEventServiceStub.find.resolves(foundTeamEvent);

        // WHEN
        comp.beforeRouteEnter({ params: { teamEventId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.teamEvent).toBe(foundTeamEvent);
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
