import { TestBed } from '@angular/core/testing';

import { SensorChartDataService } from './sensor-chart-data.service';

describe('SensorChartDataService', () => {
  let service: SensorChartDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SensorChartDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
