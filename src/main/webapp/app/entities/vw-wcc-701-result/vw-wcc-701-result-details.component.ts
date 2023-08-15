import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVwWcc701Result } from '@/shared/model/vw-wcc-701-result.model';
import VwWcc701ResultService from './vw-wcc-701-result.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class VwWcc701ResultDetails extends Vue {
  @Inject('vwWcc701ResultService') private vwWcc701ResultService: () => VwWcc701ResultService;
  @Inject('alertService') private alertService: () => AlertService;

  public vwWcc701Result: IVwWcc701Result = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vwWcc701ResultId) {
        vm.retrieveVwWcc701Result(to.params.vwWcc701ResultId);
      }
    });
  }

  public retrieveVwWcc701Result(vwWcc701ResultId) {
    this.vwWcc701ResultService()
      .find(vwWcc701ResultId)
      .then(res => {
        this.vwWcc701Result = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
