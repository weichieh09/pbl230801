/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VwWcc701ResultDetailComponent from '@/entities/vw-wcc-701-result/vw-wcc-701-result-details.vue';
import VwWcc701ResultClass from '@/entities/vw-wcc-701-result/vw-wcc-701-result-details.component';
import VwWcc701ResultService from '@/entities/vw-wcc-701-result/vw-wcc-701-result.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('VwWcc701Result Management Detail Component', () => {
    let wrapper: Wrapper<VwWcc701ResultClass>;
    let comp: VwWcc701ResultClass;
    let vwWcc701ResultServiceStub: SinonStubbedInstance<VwWcc701ResultService>;

    beforeEach(() => {
      vwWcc701ResultServiceStub = sinon.createStubInstance<VwWcc701ResultService>(VwWcc701ResultService);

      wrapper = shallowMount<VwWcc701ResultClass>(VwWcc701ResultDetailComponent, {
        store,
        localVue,
        router,
        provide: { vwWcc701ResultService: () => vwWcc701ResultServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVwWcc701Result = { id: 123 };
        vwWcc701ResultServiceStub.find.resolves(foundVwWcc701Result);

        // WHEN
        comp.retrieveVwWcc701Result(123);
        await comp.$nextTick();

        // THEN
        expect(comp.vwWcc701Result).toBe(foundVwWcc701Result);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVwWcc701Result = { id: 123 };
        vwWcc701ResultServiceStub.find.resolves(foundVwWcc701Result);

        // WHEN
        comp.beforeRouteEnter({ params: { vwWcc701ResultId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.vwWcc701Result).toBe(foundVwWcc701Result);
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
