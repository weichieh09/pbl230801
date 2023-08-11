/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VwEventResultDetailComponent from '@/entities/vw-event-result/vw-event-result-details.vue';
import VwEventResultClass from '@/entities/vw-event-result/vw-event-result-details.component';
import VwEventResultService from '@/entities/vw-event-result/vw-event-result.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('VwEventResult Management Detail Component', () => {
    let wrapper: Wrapper<VwEventResultClass>;
    let comp: VwEventResultClass;
    let vwEventResultServiceStub: SinonStubbedInstance<VwEventResultService>;

    beforeEach(() => {
      vwEventResultServiceStub = sinon.createStubInstance<VwEventResultService>(VwEventResultService);

      wrapper = shallowMount<VwEventResultClass>(VwEventResultDetailComponent, {
        store,
        localVue,
        router,
        provide: { vwEventResultService: () => vwEventResultServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVwEventResult = { id: 123 };
        vwEventResultServiceStub.find.resolves(foundVwEventResult);

        // WHEN
        comp.retrieveVwEventResult(123);
        await comp.$nextTick();

        // THEN
        expect(comp.vwEventResult).toBe(foundVwEventResult);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVwEventResult = { id: 123 };
        vwEventResultServiceStub.find.resolves(foundVwEventResult);

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
