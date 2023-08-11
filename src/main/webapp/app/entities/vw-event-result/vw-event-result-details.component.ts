import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVwEventResult } from '@/shared/model/vw-event-result.model';
import VwEventResultService from './vw-event-result.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class VwEventResultDetails extends Vue {
  @Inject('vwEventResultService') private vwEventResultService: () => VwEventResultService;
  @Inject('alertService') private alertService: () => AlertService;

  public vwEventResult: IVwEventResult = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vwEventResultId) {
        vm.retrieveVwEventResult(to.params.vwEventResultId);
      }
    });
  }

  public retrieveVwEventResult(vwEventResultId) {
    this.vwEventResultService()
      .find(vwEventResultId)
      .then(res => {
        this.vwEventResult = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
