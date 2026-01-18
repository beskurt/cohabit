package domain

import "github.com/google/uuid"

type Apartment struct {
	ID      uuid.UUID `json:"id" gorm:"type:uuid;default:gen_random_uuid();primaryKey"`
	OwnerID uuid.UUID `json:"owner_id"`

	ComplexID uuid.UUID           `json:"complex_id"`
	Complex   *ResidentialComplex `json:"complex,omitempty" gorm:"foreignKey:ComplexID"`

	DoorNumber string `json:"door_number"`
	Floor      int    `json:"floor"`

	Title       string `json:"title,omitempty"`
	Description string `json:"description,omitempty"`

	AvgRating float64 `json:"avg_rating"`
}
