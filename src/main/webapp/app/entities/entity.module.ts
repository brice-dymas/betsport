import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BetsportV2PlayerModule } from './player/player.module';
import { BetsportV2EmployeeModule } from './employee/employee.module';
import { BetsportV2AssignmentModule } from './assignment/assignment.module';
import { BetsportV2PeriodModule } from './period/period.module';
import { BetsportV2TransactionTypeModule } from './transaction-type/transaction-type.module';
import { BetsportV2AmountModule } from './amount/amount.module';
import { BetsportV2TransactionModule } from './transaction/transaction.module';
import { BetsportV2CashDeskModule } from './cash-desk/cash-desk.module';
import { BetsportV2ManageModule } from './manage/manage.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BetsportV2PlayerModule,
        BetsportV2EmployeeModule,
        BetsportV2AssignmentModule,
        BetsportV2PeriodModule,
        BetsportV2TransactionTypeModule,
        BetsportV2AmountModule,
        BetsportV2TransactionModule,
        BetsportV2CashDeskModule,
        BetsportV2ManageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BetsportV2EntityModule {}
