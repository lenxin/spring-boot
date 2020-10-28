package smoketest.data.jpa.service;

import smoketest.data.jpa.domain.Rating;

public interface ReviewsSummary {

	long getNumberOfReviewsWithRating(Rating rating);

}
