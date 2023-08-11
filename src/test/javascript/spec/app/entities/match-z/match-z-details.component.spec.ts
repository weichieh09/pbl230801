/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MatchZDetailComponent from '@/entities/match-z/match-z-details.vue';
import MatchZClass from '@/entities/match-z/match-z-details.component';
import MatchZService from '@/entities/match-z/match-z.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MatchZ Management Detail Component', () => {
    let wrapper: Wrapper<MatchZClass>;
    let comp: MatchZClass;
    let matchZServiceStub: SinonStubbedInstance<MatchZService>;

    beforeEach(() => {
      matchZServiceStub = sinon.createStubInstance<MatchZService>(MatchZService);

      wrapper = shallowMount<MatchZClass>(MatchZDetailComponent, {
        store,
        localVue,
        router,
        provide: { matchZService: () => matchZServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatchZ = { id: 123 };
        matchZServiceStub.find.resolves(foundMatchZ);

        // WHEN
        comp.retrieveMatchZ(123);
        await comp.$nextTick();

        // THEN
        expect(comp.matchZ).toBe(foundMatchZ);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMatchZ = { id: 123 };
        matchZServiceStub.find.resolves(foundMatchZ);

        // WHEN
        comp.beforeRouteEnter({ params: { matchZId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.matchZ).toBe(foundMatchZ);
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
