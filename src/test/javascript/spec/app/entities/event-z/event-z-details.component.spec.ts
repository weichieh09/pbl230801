/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EventZDetailComponent from '@/entities/event-z/event-z-details.vue';
import EventZClass from '@/entities/event-z/event-z-details.component';
import EventZService from '@/entities/event-z/event-z.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EventZ Management Detail Component', () => {
    let wrapper: Wrapper<EventZClass>;
    let comp: EventZClass;
    let eventZServiceStub: SinonStubbedInstance<EventZService>;

    beforeEach(() => {
      eventZServiceStub = sinon.createStubInstance<EventZService>(EventZService);

      wrapper = shallowMount<EventZClass>(EventZDetailComponent, {
        store,
        localVue,
        router,
        provide: { eventZService: () => eventZServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEventZ = { id: 123 };
        eventZServiceStub.find.resolves(foundEventZ);

        // WHEN
        comp.retrieveEventZ(123);
        await comp.$nextTick();

        // THEN
        expect(comp.eventZ).toBe(foundEventZ);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEventZ = { id: 123 };
        eventZServiceStub.find.resolves(foundEventZ);

        // WHEN
        comp.beforeRouteEnter({ params: { eventZId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.eventZ).toBe(foundEventZ);
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
